(ns everine.db
  (:require [taoensso.carmine :as car :refer [wcar]])
  (:gen-class))

(def ^:private redis-config {:pool {} :spec {:host "redis"}})

(defn- redis-set [key value] (wcar redis-config (car/set key value)))
(defn- redis-get [key] (wcar redis-config (car/get key)))

(def new-user-data  {:current "test list"
                     :lists [{:name "test list"
                              :items [{:id 1 :label "do something"}
                                      {:id 2 :label "do nothing" :done true}]}
                             {:name "empty list"
                              :items [{:id 1 :label "got you!" :done true}]}]})

(defn user-data [user-id]
  (or (redis-get user-id) new-user-data))

(defn set-user-data [user-id data]
  (= "OK" (redis-set user-id data)))
