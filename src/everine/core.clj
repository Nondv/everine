(ns everine.core
  (:use ring.middleware.resource)
  (:gen-class))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(def app (wrap-resource handler "public"))
