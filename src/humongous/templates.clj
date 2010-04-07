(ns humongous.templates
  (:use [hiccup :only [html]]
        [hiccup.form-helpers :only [select-options]]
        [hiccup.page-helpers :only [include-js include-css unordered-list]]
        [humongous.mongo :only [*mongo*]]
        [karras :only [mongo-db list-collections list-indexes collection]]))

(defn layout [content]
  (html
   [:html 
    [:head
     (include-js "/js/jquery-1.4.2.min.js"
                 "/js/jquery-ui-1.7.2.custom.min.js"
                 "/js/application.js"
                 "/js/jquery.ufvalidator-1.0.4.js")
     (include-css "/css/screen.css"
                  "/css/blitzer/jquery-ui-1.7.2.custom.css"
                  "/css/application.css")
     [:title "Humongous"]]
    [:body
     [:div.container
      [:h1 [:a {:href "/"} "Humongous"]]
      content]]]))

(defn index [req]
  (let [dbs (.getDatabaseNames *mongo*)]
    (layout [:div#main
             [:h2#databases "Databases"]
             (map (fn [db] [:div.db
                            [:h3 [:a {:href (str "/" db)} db]]])
                  dbs)])))

(defn index-partial [db col-name]
  [:div.index
   [:h5 "Indexes"]
   [:ul.index-listing
     (map (fn [index] (map (fn [[k v]] [:li (name k)]) (:key index)))
             (list-indexes (collection db col-name)))]])

(defn collection-partial [db col-name]
  (let [collection-url (str "/" db "/" (name col-name))]
    [:div.collection
     [:h4 [:a {:href collection-url} (name col-name)]]]))

(defn db [req]
  (let [db (mongo-db *mongo* (:db (:params req)))]
    (layout [:div#main
             [:h2 (str db)]
             [:div#db-nav.navigation
              [:a.new-collection {:href ""} "New Collection"]
              " | "
              [:a.drop-db {:href (str "/" db "/drop")} "Drop DB"]]
             [:div.new-collection-form.hidden
              [:form {:method "POST" :action (str "/" db "/collection/new")}
               [:p [:label {:for "collection-name"} "Name: "]
                [:input {:type "text" :class "req-string" :id "collection-name" :name "collection-name" :value ""}]]
               [:div.fields
                [:p
                 [:button {:class "fg-button ui-state-default fg-button-icon-left ui-corner-all ui-state-hover"} [:span {:class "ui-icon ui-icon-plusthick"}] "Add Field"]]
                [:p
                 [:input {:class "req-string" :type "text" :name "fields[]" :value ""}]]]
               [:p
                 [:input {:type "submit" :value "Create"}]]]]
             [:h3 "Collections"]
             (map #(collection-partial db %)
                  (list-collections db))])))

(defn db-collection [req]
  (let [db (mongo-db *mongo* (:db (:params req)))
        col (collection db (:col (:params req)))
        one-item (.findOne col)]
    (layout [:div#main
             [:h2 (.getFullName col)]
             [:div#collection-nav.navigation
              [:a.new-index {:href ""} "New Index"]
              " | "
              [:a.drop-indexes {:href (str "/" db "/" col "/indexes/drop")} "Drop Indexes"]
              " | "
              [:a.drop-collection {:href (str "/" db "/" col "/drop")} "Drop Collection"]]
             [:div.new-index-form.hidden
              [:form {:method "POST" :action (str "/" db "/" col "/indexes/new")}
               [:p
                [:label {:for "index-name"} "Name: "]
                [:input {:type "text" :class "req-string" :id "index-name" :name "index-name" :value ""}]]
               [:p [:input {:type "submit" :value "Create"}]]]]
             [:div.collection-summary.ui-state-highlight
              [:h3.clickable-header "Summary"]
              [:div#summary-content.hidden
               [:p#count (str "Current number of documents: " (.getCount col))]
               [:div#sample
                [:p "Sample:"]
                [:p#one.last one-item]]]]
             [:div#collection-query-form
              [:form {:action "" :method "post"}
               [:h4 "Query"]
               [:p [:button#add-field.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only [:span.ui-button-text "Add Field"]]]
               [:div#fields
                [:p.def-field [:select#db-field {:name "db-field"}
                               (select-options (remove #(= "_ns" %) (keys one-item)))]
                 [:select#db-predicate {:name "db-field"}
                  (select-options ["gt" "gte" "lt" "lte" "eq" "ne" "in" "nin" "mod" "all" "exists"])]
                 [:input#predicate-val {:name "predicate-val"}]]]
               [:div#query-items]
               [:p [:input#submit-query.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-text-only {:type "submit" :value "Submit"}]]]]
             (index-partial db col)])))

(defn db-collection-query [req]
  (prn req))
