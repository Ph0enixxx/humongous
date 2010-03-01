(ns humongous
  (:gen-class)
  (:use [humongous.core :only [app]]
        [ring.adapter.jetty :only [run-jetty]]))

(defn -main [& args]
  (let [[port db-name] args]
    (run-jetty app
               {:port (Integer. (or port 8080))})))