# heart_watcher_patient

Client application representing a sensor that watches heart rate, movement and pressure and sends these data to a server.

# communication protocol

- subscribes

```json
{
	"source" : "sensor",
	"action" : "register",
	"name" : "John"
}
```

- receives from server after subscribing

```json
{
	"id" : 0,
	"message" : "you are registered to the server"
}
```


- starts sending patient's data

```json
{
	"source" : "sensor",
	"action" : "send",
	"id" : 1,
	"payload" : {
		"movement" : true,
		"heart_rate" : 110,
		"pressure" : [120, 80]
	}
}
```

- receives from server after sending data

```json
{
	"message" : "data received"	
}
```
