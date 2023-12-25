- `docker build -t sc-mongodb ./mongo`
- `docker run -d --network scn --name rabbitmq -p 5672:5672 -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=root rabbitmq:latest`

- `docker build -t auth ../AuthMicroService`
- `docker build -t gateway ../ApiGatewayService`
- `docker build -t card ../CardMicroService`
- `docker build -t user ../UserMicroService`
- `docker build -t mail ../MailService`

* `docker run -d --name=sc-mongodb -p 27020:27020 sc-mongodb`
  - `docker exec -it sc-mongodb mongosh`
  - `use studentcard`
  - `db.init.insert({key:"value"})`
  - `db.createUser({ user:"studentcarduser", pwd:"root", roles:[ {role:"readWrite", db:"studentcard"} ] })`
* `docker run -d --name=sc-rabbitmq -p 5672:5672 sc-rabbitmq`

- `docker run -d --name=auth -p 4040:4040 auth`
- `docker run -d --name=gateway --network=sc-network -p 443:443 -e KEYSTORE_JKS_PATH=/cert/keystore.jks -e KEYSTORE_PASSWORD=123456 eminarikan/gateway:v0.2`
- `docker run -d --name=card -p 4042:4042 card`
- `docker run -d --name=mail -p 4043:4043 mail`
- `docker run -d --name=user -p 4041:4141 user`