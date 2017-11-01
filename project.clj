(defproject everine "0.1.0-SNAPSHOT"
  :min-lein-version "2.8.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [rum "0.10.8"]
                 [cljs-ajax "0.7.2"]
                 [com.taoensso/carmine "2.16.0"]
                 [environ "1.1.0"]
                 [ring "1.6.2"]
                 [ring/ring-json "0.4.0"]
                 [compojure "1.6.0"]
                 [ring/ring-jetty-adapter "1.5.0"]]
  :main everine.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :uberjar-name "everine-standalone.jar"
  :plugins [[lein-ring "0.9.7"]
            [cider/cider-nrepl "0.16.0-SNAPSHOT"]
            [lein-cljsbuild "1.1.7"]]
  :aliases {"hrepl" ["repl" ":headless" ":host" "0.0.0.0" ":port" "7888"]}
  ;; :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds
              [{:id "cljsbuild" ;; name for a build
                :source-paths ["src-cljs"]
                :compiler {:output-to "resources/public/javascripts/main.js"
                           :optimizations :whitespace
                           :pretty-print true}}
               {:id "production"
                :jar true
                :source-paths ["src-cljs"]
                :compiler {:output-to "resources/public/javascripts/main.js"
                           :optimizations :advanced
                           :pretty-print false}}]}
  :ring {:handler everine.core/app
         :open-browser? false})
