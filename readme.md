## Summary

`octane-bridge-components` is a set of a components dedicated to provide bridge solution for SaaS to on-premises connectivity.

These components are to be used as a **dependencies** in a hosting applications, none of them is actually runnable.

#### Bridge Server Component

Responsibility of this component is to provide connectivity endpoints for a remove bridged service/s interop.

Each endpoint should be capable of:
- handling connection of multiple services of the specific type (for instance - CI Servers endpoint will handle bridges of all CI Servers)
- managing connected services and their statuses
- delivering messages to the remote service and receiving responses (in-bond and out-bond)
- allow to submit request and synchronously wait for the response from hosting application perspective
- functioning correctly in clustered environment

#### Bridge Client Component

Responsibility of this component is to provide smooth interaction with the underlying transport facility (WS, probably HTTP as a fallback in the future) while receiving messages from the server, and submitting responses to the server.

Client should be capable of managing multiple bridges, each being capable of:
- initiating stable connectivity with corresponding server's endpoint
- retrieve messages from endpoint and deliver them to the hosting application
- send appropriate response to the server's endpoint if/when relevant and available