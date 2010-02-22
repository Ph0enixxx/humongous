(ns humongous.core
  (:use [compojure :only [defroutes POST GET PUT DELETE ANY]]
        [humongous.middleware :only [wrap-classpath-public wrap-connection]]
        [humongous.response :only [success]]
        [humongous.templates :only [index db db-collection]]
        [humongous.mongo :only [create-index create-collection drop-collection drop-db drop-indexes]]
        [ring.middleware.params :only [wrap-params]]))

(defroutes routes
  (GET "/" req (success (index req)))
  (GET "/:db" req (success (db req)))
  (GET "/:db/drop" req (drop-db req))
  (POST "/:db/collection/new" req (create-collection req))
  (GET "/:db/:col" req (success (db-collection req)))
  (GET "/:db/:col/drop" req (drop-collection req))
  (POST "/:db/:col/indexes/new" req (create-index req))
  (GET "/:db/:col/indexes/drop" req (drop-indexes req)))

(defn app []
  (wrap-classpath-public
   (wrap-connection
    (wrap-params routes))
   "public"))