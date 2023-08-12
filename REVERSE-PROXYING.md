# Cars Reservation System - Reverse proxying

A typical distributed system usually wants to be safe from attacks. One of the affordable attack types is the Dedicated Denial of Service attack (DDOS). One of the best ways to prevent such attacks is to never reveal the direct addresses and domains of the apps, and instead to always reverse proxy it with some powerful tools.

CloudFlare is such a tool. It accepts every request sent to an arbitrary domain and resends it to the entrypoint of the application cluster. The request is now authored not by the original sender but by a CloudFlare robot instead. To prevent the loss of the requests' fingerprint there are some HTTP headers commonly used to preserve this information (X_FORWARDED_FOR, stickyness tokens and others).

This project is reverse proxied by CloudFlare.