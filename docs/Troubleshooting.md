> *Refer to* [*README*](../README.md) *for main instruction file*

# Troubleshooting

## General issues

* If the [**MCOP MCPTT Client**](../README.md) app is closed without having deregistered, it might fail the next time is opened. To solve it, go to the device preferences, and under installed applications, Force Stop the **MCOPSDK** app.

## Network issues

Due to the fact that in most cases users and developers use access networks that work with NAT and Firewalls (e.g. business or university networks), there is some common functionality issues that may arise:

* **Reception of modified SIP signalling messages**:

	In some networks, Firewalls might be able to modify the content of the SIP messages, resulting in the destination not receiving the messages as they were sent.

* **Deletion of SIP signalling messages**:
	
	Some Firewalls as able to delete specific SIP messaged to avoid stablishing the communication.

* **Closed network ports**:

	It is common for network operators to block most used ports in SIP communications, like port 5060.

* **Change from private to public IPs**:

	When behind a NAT network, the local IP of the device is not the public IP that messages reach the server with. This can cause errors processing the messages, specially on the server side.

* **Non-reception of floor control and RTP messages at the beginning of the communication**:

	MCPTT sessions need sending and receiving RTP and floor control data. In networks with NAT it is common not to receive data from a certain port until the device in that network sends a message using that port first. This causes not to receive data until communication in both directions has happened. Thus, granting and releasing the token from both communication sides might be needed one first time before any audio can be sent.

For the moment, the MCOP SDK doesn't provide any solution for this type of situations, but will try to reduce their impact in the future.
