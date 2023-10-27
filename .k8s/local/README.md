# Launching the project locally


## 1. A command to initialize minikube cluster

```bash
minikube start --mount-string="$(pwd):/minikube-host" --mount --addons=ingress,coredns
```

Execute in the root of the project. (pwd = repo root)

## 2. DNS hack

There's an issue in Nextauth library. It currently does not support separate backend and frontend SSO urls:
https://github.com/nextauthjs/next-auth/issues/2637

So, as a workaround I used little CoreDNS trick:
`kubectl edit configmap coredns -n kube-system`

And added the line:

```CoreDNS
apiVersion: v1
data:
  Corefile: |
    .:53 {
        log
        errors
        rewrite name sso.carres.local <your keycloak node IP address, e.g. 192.168.49.2> <-------- this one
        health {
           lameduck 5s
        }
        ready
        kubernetes cluster.local in-addr.arpa ip6.arpa {
           pods insecure
           fallthrough in-addr.arpa ip6.arpa
           ttl 30
        }
        prometheus :9153
        hosts {
           192.168.65.254 host.minikube.internal
           fallthrough
        }
        forward . /etc/resolv.conf {
           max_concurrent 1000
        }
        cache 30
        loop
        reload
        loadbalance
    }
kind: ConfigMap
metadata:
  creationTimestamp: "2023-08-08T16:30:41Z"
  name: coredns
  namespace: kube-system
  resourceVersion: "49266"
  uid: a9e1aed7-8b58-436b-b16d-a79932ecbfe0
```

This line enables website app to access the SSO over the external address.

## 3. /etc/hosts records

Put the following lines to the bottom of `/etc/hosts` file:

```hosts
127.0.0.1	host.minikube.internal
127.0.0.1	carres.local
127.0.0.1	api.carres.local
127.0.0.1	sso.carres.local
127.0.0.1	rmq.carres.local
```

This would enable the browser to access the whole ecosystem locally.

## 4. Apply Kubernetes configs:

```bash
kubectl apply -f .k8s/local/\*.yaml
```

(Please remember to replace `kubectl apply` with `minikube kubectl -- apply` if you use Windows WSL)

It takes about 1 minute for the project to start due to Keycloak initialization.


## 5. (For Windows / OSX)

Run tunnelling:

```
minikube tunnel
```