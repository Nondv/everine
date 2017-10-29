(ns everine.core
  (:require [rum.core :as rum]))

(enable-console-print!)

(println "Hello World!")
(println "This is ClojureScript!")

(rum/defc label [text]
   [:div {:class "label"} text])

(rum/mount (label "Yarrr!!") (js/document.getElementById "app" ))
