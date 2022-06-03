(ns curso4.logic
  (:require [curso4.model :as h.model])
  (:import (java.util Date)))

(defn agora []
  (h.model/to-ms (Date.)))