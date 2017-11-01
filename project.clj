(defproject everine "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [rum "0.10.8"]
                 [ring "1.6.2"]
                 [compojure "1.6.0"]
                 [ring/ring-jetty-adapter "1.5.0"]]
  :main ^:skip-aot everine.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.7"]]
  :cljsbuild {:builds
              [{:id "cljsbuild" ;; name for a build
                :source-paths ["src-cljs"]
                :compiler {:output-to "resources/public/javascripts/main.js"
                           :optimizations :whitespace
                           :pretty-print true}}]}
  :ring {:handler everine.core/app
         :open-browser? false})
