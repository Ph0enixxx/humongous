(ns humongous.templates
  (:use [hiccup.core :only [html]]
        [hiccup.form-helpers :only [select-options]]
        [hiccup.page-helpers :only [include-js include-css unordered-list]]))

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