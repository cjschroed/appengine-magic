(in-ns 'appengine-magic.services.blobstore)


(defn get-uploads [ring-request-map]
	(.println System/out "Calling getUploads() production")
  (let [^:HttpServletRequest request (:request ring-request-map)]
    (into {} (.getUploads (get-blobstore-service) request))))

;			raw-uploaded-blobs (slurp (.getInputStream request))
;        uploaded-blobs (read-string raw-uploaded-blobs)
;        processed-blobs (reduce (fn [acc [upload-name blob-key-str]]
;      									 	(assoc acc upload-name (BlobKey. blob-key-str)))
;                                {} uploaded-blobs)]
;    processed-blobs))


(defn uploaded-blobs [ring-request-map]
  (let [^:HttpServletRequest request (:request ring-request-map)]
    (into {} (.getUploadedBlobs (get-blobstore-service) request))))
