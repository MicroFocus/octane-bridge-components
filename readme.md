## Summary

`octane-bridge-components` is a set of a components dedicated to provide bridge solution for SaaS to on-premises connectivity.

These components are to be used as a **dependencies** in a hosting applications, none of them is actually runnable.

#### Bridge Server Component

Responsibility of this component is to provide connectivity endpoints for a remove bridged service/s interop.

Each endpoint should be capable of:
- handling connection of multiple services of the specific type (for instance - CI Servers endpoint will handle bridges of all CI Servers)
- managing connected services and their statuses
- delivering messages to the remote service and receiving responses (in-bond [as a response to a request] and out-bond [just a client message on it's own])
- allow to submit request and synchronously wait for the response from hosting application perspective
- functioning correctly in clustered environment

Find detailed info as of how to consume this component [here](octane-bridge-server/docs/readme.md)

#### Bridge Client Component

Responsibility of this component is to provide smooth interaction with the underlying transport facility (WS, probably HTTP as a fallback in the future) while receiving messages from the server, and submitting responses to the server.

Client should be capable of managing multiple bridges, each being capable of:
- initiating stable connectivity with corresponding server's endpoint
- retrieve messages from endpoint and deliver them to the hosting application
- send appropriate response to the server's endpoint if/when relevant and available

Find detailed info as of how to consume this component [here](octane-bridge-client/docs/readme.md)
