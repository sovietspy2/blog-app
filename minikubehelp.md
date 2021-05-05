- delete previous cluster
  `minikube delete`

- start minikube
`minikube start`

- enable ingress and fix a windows bug  
`minikube addons enable ingress`  
`kubectl delete -A ValidatingWebhookConfiguration ingress-nginx-admission`
  
- setup cluster
`kubectl apply -f kube`

- get ingress IP (dev only)
`kubectl get ingress blogapp-ingress` -> `172.27.163.37`

- update hosts file (dev only)
`172.27.163.37 blogapp.local`
  
- open browser: http://blogapp.local/graphiql
