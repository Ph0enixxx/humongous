(ns humongous.middleware
  (:use [humongous.request :only [url-decode]]
        [humongous.response :only [success]])
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
          (if-let [file (ClassLoader/getSystemResource (str dir "/" path))]
            (success (.openStream file))
            (app req))))
      (app req))))