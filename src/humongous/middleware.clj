(ns humongous.middleware
  (:use [humongous.request :only [url-decode param-map]]
        [humongous.response :only [success]]))

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

(defn wrap-form-params [app]
  (fn [req]
    (if-not (#{:get :head} (:request-method req))
      (if-not (contains? (:headers req) "application/x-www-form-urlencoded")
        (let [new-req (merge req {:form-params (param-map req)})]
          (app new-req))))
    (app req)))