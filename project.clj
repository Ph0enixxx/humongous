(defproject humongous "0.1.0-SNAPSHOT"
  :description "Humongous: a Clojure MongoDB web interface"
  :dependencies [[compojure "0.4.0-SNAPSHOT"]
                 [hiccup "0.4.0-SNAPSHOT"]
                 [karras "0.2.0-SNAPSHOT"]
                 [org.clojure/clojure "1.1.0"]
                 [org.clojure/clojure-contrib "1.1.0"]
                 [ring/ring-core "0.2.0"]
                 [ring/ring-jetty-adapter "0.2.0"]]
  :dev-dependencies [[swank-clojure "1.1.0"]]
  :main humongous)
