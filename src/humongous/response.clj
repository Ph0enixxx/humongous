(ns humongous.response)

(defn success [body]
  {:status 200 :headers {} :body body})

(defn redirect-to [location]
  {:status 302 :headers {"Location" location}})

(defn not-found [request]
  {:status 404 :body (str "<h1>404 - Not Found:" (:uri request) "</h1>")})