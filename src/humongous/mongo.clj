(ns humongous.mongo
  (:use [humongous.response :only [redirect-to]]
        [karras :only [mongo-db collection ensure-index insert]]
        [karras.sugar :only [asc]]))

(declare *mongo*)

(defn create-collection [req]
  (prn req)
  (let [db (mongo-db *mongo* (:db (:params req)))
        col (collection db (get (:form-params req) "collection-name"))]
    (insert col (reduce (fn [m v] (conj m [v nil])) {} (:fields (:form-params req))))
    (redirect-to (str "/" db))))

(defn create-index [req]
  (let [db (mongo-db *mongo* (:db (:params req)))
        col (collection db (:col (:params req)))]
    (ensure-index col (asc (get (:form-params req) "index-name")))
    (redirect-to (str "/" db "/" col))))

(defn drop-collection [req]
  (let [db (mongo-db *mongo* (:db (:params req)))
        col (collection db (:col (:params req)))]
    (.drop col)
    (redirect-to (str "/" db))))

(defn drop-db [req]
  (let [db (mongo-db *mongo* (:db (:params req)))]
    (.dropDatabase db)
    (redirect-to "/")))

(defn drop-indexes [req]
  (let [db (mongo-db *mongo* (:db (:params req)))
        col (collection db (:col (:params req)))]
    (.dropIndexes col)
    (redirect-to (str "/" db "/" col))))
