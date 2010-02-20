(ns humongous.core
  (:use [compojure :only [defroutes POST GET PUT DELETE ANY]]
        [humongous.middleware :only [wrap-classpath-public wrap-form-params]]
        [humongous.response :only [success]]))

(defroutes routes
  (GET "*" req (success (str req))))

(defn app []
  (wrap-classpath-public
   (wrap-form-params routes)
   "public"))
