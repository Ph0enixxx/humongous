(ns humongous.core
  (:use [compojure :only [defroutes POST GET PUT DELETE ANY]]
        [humongous.middleware :only [wrap-classpath-public wrap-connection]]
        [humongous.response :only [success]]
        [humongous.templates :only [index db db-collection]]
        [ring.middleware.params :only [wrap-params]]))

(defroutes routes
  (GET "/:db/:col" req (success (db-collection req)))
  (GET "/:db" req (success (db req)))
  (GET "/" req (success (index req))))

(defn app []
  (wrap-classpath-public
   (wrap-connection
    (wrap-params routes))
   "public"))