- delete previous cluster
  `minikube delete`

- start minikube
`minikube start`

- enable ingress and fix a windows bug  
`minikube addons enable ingress`  
`kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission`
  
- setup deployment
`kubectl create -k ./kube/overlays/dev`

- get ingress IP (dev only)
`kubectl get ingress blogapp-ingress` -> `172.27.163.37`

- update hosts file (dev only)
`172.27.163.37 blogapp.local`
  
- open browser: http://blogapp.local/graphiql

- delete deployment
  `kubectl delete -k ./kube/overlays/dev`
