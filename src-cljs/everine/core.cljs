(ns everine.core
  (:use [everine.components.todo-list :only [todo-list]])
  (:require [rum.core :as rum]))

(enable-console-print!)

(println "Hello World!")
(println "This is ClojureScript!")

(def items [{:id 1 :label "item1"} {:id 2 :label "item2"} {:id 3 :label "item3"}])

(rum/defcs app < (rum/local items ::items) [state]
  (todo-list (::items state)))

(rum/mount (app) (js/document.getElementById "app" ))
