(ns everine.core
  (:use ring.middleware.resource
        ring.middleware.content-type)
  (:gen-class))


(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello World"})

(def app
  (-> handler
      (wrap-resource "public")
      wrap-content-type))
