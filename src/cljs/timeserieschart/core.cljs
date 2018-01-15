(ns timeserieschart.core
  (:require-macros [cljs.core.match :refer [match]])
  (:require [cljs.core.match :as m]
            [re-alm.core :as ra]
            [reagent.core :as r]))

(def pondjs (aget js/window "deps" "pondjs"))
(def TimeSeries (.-TimeSeries pondjs))
(def TimeRange (.-TimeRange pondjs))

(def react-timeseries-charts (aget js/window "deps" "react-timeseries-charts"))
(def Charts (r/adapt-react-class (.-Charts react-timeseries-charts)))
(def ChartContainer (r/adapt-react-class (.-ChartContainer react-timeseries-charts)))
(def ChartRow (r/adapt-react-class (.-ChartRow react-timeseries-charts)))
(def YAxis (r/adapt-react-class (.-YAxis react-timeseries-charts)))
(def LineChart (r/adapt-react-class (.-LineChart react-timeseries-charts)))

(def data {:name    "traffic"
           :columns ["time" "in" "out"]
           :points  [[1400425947000 52 41]
                     [1400425948000 18 45]
                     [1400425949000 26 49]
                     [1400425950000 93 81]]})

(def series1 (TimeSeries. (clj->js data)))

(def timerange (.timerange series1))

(defn- init-counter []
  {:value 0})

(defn- render-counter [model dispatch]
  [:div
   {:style {:padding-bottom "10px"}}
   [ChartContainer {:time-range timerange
                    :width      800}
    [ChartRow {:height 400}
     [YAxis {:id "axis1" :min 0 :max 100}]
     [Charts
      [LineChart {:axis    "axis1"
                  :columns ["in"]
                  :series  series1}]]]]])

(defn- update-counter [model msg]
  (match msg
         :inc
         (update-in model [:value] inc)

         :dec
         (update-in model [:value] dec)

         _
         model))

(def counter-component
  {:render #'render-counter
   :update #'update-counter})
