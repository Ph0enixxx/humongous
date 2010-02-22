(ns humongous.templates
  (:use [hiccup.core :only [html]]
        [hiccup.form-helpers :only [select-options]]
        [hiccup.page-helpers :only [include-js include-css unordered-list]]
        [humongous.mongo :only [*mongo*]]
        [karras :only [mongo-db list-collections list-indexes collection]]))

(defn layout [content]
  (html
   [:html 
    [:head
     (include-js "/js/jquery-1.4.2.min.js"
                 "/js/application.js")
     (include-css "/css/screen.css"
                  "/css/ie.css"
                  "/css/application.css")
     [:title "Humongous"]]
    [:body
     [:div.container
      [:h1 "Humongous"]
      content]]]))

(defn index [req]
  (let [dbs (.getDatabaseNames *mongo*)]
    (layout (html [:h2 "Databases"]
                  (map (fn [db] [:div.db
                                 [:p [:a {:href (str "/" db)} db]]])
                       dbs)))))

(defn index-partial [db col-name]
  (html [:p [:a {:href "#" :class "indexes-toggle"} "Indexes"]]
        [:ul.index-listing.hidden
         (map (fn [index] (map (fn [[k v]] [:li (name k)]) (:key index)))
              (list-indexes (collection db col-name)))]))

(defn collection-partial [db col-name]
  (html [:h4 (name col-name)]
        (index-partial db col-name)))

(defn db [req]
  (let [db (mongo-db *mongo* (:db (:params req)))]
    (layout (html [:h2 (str db)]
                  [:h3 "Collections"]
                  (map #(collection-partial db %)
                       (list-collections db))))))

(defn db-collection [req]
  (let [db (mongo-db *mongo* (:db (:params req)))
        col (collection db (:col (:params req)))]
    (layout (html [:h2 col]
                  (index-partial db col)))))