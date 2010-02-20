(ns humongous.request
  (:use [clojure.contrib duck-streams str-utils])
  (:import [java.net URLDecoder]))

(defn url-decode
  "Returns the form-url-decoded version of the given string."
  [encoded]
  (URLDecoder/decode encoded "UTF-8"))

(defn param-map [request]
  (let [post-str (first (read-lines (reader (:body request))))
        segments (re-split #"&" post-str)
        decoded (map url-decode segments)
        pairs (map #(re-split #"=" % 2) decoded)]
    (into {} (map (fn [[k v]] 
                    (assoc {} (keyword k) (if (empty? v) nil v)))
                  pairs))))