(in-ns 'appengine-magic.services.blobstore)


(defn get-uploads-google [ring-request-map]
  (.println System/out "Calling getUploads() production")
  (let [^:HttpServletRequest request (:request ring-request-map)]
    (into {} (.getUploads (get-blobstore-service) request))))
		
(defn get-uploads-local [ring-request-map]
  (.println System/out "Calling getUploads() local")
  (let [^:HttpServletRequest request (:request ring-request-map)
        raw-uploaded-blobs (slurp (.getInputStream request))
        uploaded-blobs (read-string raw-uploaded-blobs)
        processed-blobs (reduce (fn [acc [upload-name blob-key-str]]
                                  (assoc acc upload-name (BlobKey. blob-key-str)))
                                {}
                                uploaded-blobs)]
    processed-blobs))

(defn get-uploads [ring-request-map]
	(.println System/out "Calling getUploads() dispatch")
	(if (= :production (core/appengine-environment-type))
		(get-uploads-google ring-request-map)
		(get-uploads-local ring-request-map)))

	
(defn uploaded-blobs-google [ring-request-map]
	(.println System/out "Calling getUploadedBlobs() production")
  (let [^:HttpServletRequest request (:request ring-request-map)]
    (into {} (.getUploadedBlobs (get-blobstore-service) request))))

(defn uploaded-blobs-local [ring-request-map]
	(.println System/out "Calling getUploadedBlobs() local")
  (let [^:HttpServletRequest request (:request ring-request-map)
        raw-uploaded-blobs (slurp (.getInputStream request))
        uploaded-blobs (read-string raw-uploaded-blobs)
        processed-blobs (reduce (fn [acc [upload-name blob-key-str]]
                                  (assoc acc upload-name (BlobKey. blob-key-str)))
                                {}
                                uploaded-blobs)]
    processed-blobs))

(defn uploaded-blobs [ring-request-map]
	(.println System/out "Calling getUploadedBlobs() dispatch")
	(if (= :production (core/appengine-environment-type))
		(uploaded-blobs-google ring-request-map)
		(uploaded-blobs-local ring-request-map)))


