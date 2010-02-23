(ns humongous.middleware
  (:use [humongous.mongo :only [*mongo*]]
        [humongous.response :only [success]]
        [karras :only [connect]])
  (:import [java.net URLDecoder]))

(defn- url-decode
  "Returns the form-url-decoded version of the given string."
  [encoded]
  (URLDecoder/decode encoded "UTF-8"))

(defn wrap-classpath-public [app dir]
  (fn [req]
    (if (#{:get :head} (:request-method req))
      (let [uri (url-decode (:uri req))]
        (let [path (cond
                    (.endsWith "/" uri)        (str uri "index.html")
                    (re-find #"\.[a-z]+$" uri) uri
                    :else                      (str uri ".html"))]
          (if-let [file (ClassLoader/getSystemResource (str dir path))]
            (success (.openStream file))
            (app req))))
      (app req))))

(defn wrap-connection [app]
  (fn [req]
    (binding [*mongo* (connect)]
      (app req))))