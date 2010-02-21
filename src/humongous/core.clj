(ns humongous.core
  (:use [compojure :only [defroutes POST GET PUT DELETE ANY]]
        [humongous.middleware :only [wrap-classpath-public]]
        [humongous.response :only [success]]
        [humongous.templates :only [layout]]
        [ring.middleware.params :only [wrap-params]]))

(defroutes routes
  (GET "/" req (success (layout (str req)))))

(defn app []
  (wrap-classpath-public
   (wrap-params routes)
   "public"))