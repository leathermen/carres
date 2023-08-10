# A word of warning

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
        rewrite name sso.carres.local host.minikube.internal <-------- this one
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