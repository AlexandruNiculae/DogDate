 const Koa = require('koa');
const app = new Koa();
const server = require('http').createServer(app.callback());
const WebSocket = require('ws');
const wss = new WebSocket.Server({server});
const Router = require('koa-router');
const cors = require('koa-cors');
const bodyparser = require('koa-bodyparser');

app.use(bodyparser());
app.use(cors());
app.use(async function (ctx, next) {
    const start = new Date();
    await next();
    const ms = new Date() - start;
    console.log(`${ctx.method} ${ctx.url} ${ctx.response.status} - ${ms}ms`);
});



const router = new Router();


const broadcast = (data) =>
    wss.clients.forEach((client) => {
        if (client.readyState === WebSocket.OPEN) {
            client.send(JSON.stringify(data));
        }
    });





const users = [];
const dogs = [];

for (let i = 0; i < 10; i++) {
    dogs.push({
        key: i + 1,
        name: "Dog" + (i + 1),
		race: "Husky",
		personality: "Calm",
        age: i % 21
    });
}

for (let i = 0; i < 10; i++) {
	if (i == 0)
    users.push({
        id: i + 1,
        name: "admin",
		password: "1234",
        email: "admin@idk.com",
        type: "admin"
    });
	else
		users.push({
        id: i + 1,
        name: "User" + (i + 1),
		password: "1234",
        email: "user" + (i + 1) + "@idk.com",
        type: "guest"
    });
}

router.get('/login', ctx => {
	const headers = ctx.request.headers;
	for (let i = 0; i < users.length; i++){
		if (users[i].name === headers.username)
			if (users[i].password === headers.password) {
				ctx.response.body = {"message" : "true"};
				broadcast(users[i]);
				break;
			}
		if (i === users.length - 1)
				ctx.response.body = {"message" : "false"};
	}
    ctx.response.status = 200;
})

router.get('/user', ctx => {

	const header = ctx.request.headers;
	for (let i = 0; i < users.length; i++) {
		if (users[i].name === header.name) {
			ctx.response.body = users[i];
			ctx.response.status = 200;
			break;
		}
	}
});
router.get('/dogs', ctx => {
    ctx.response.body = dogs;
    ctx.response.status = 200;
});

router.get('/users', ctx => {
    ctx.response.body = users;
    ctx.response.status = 200;
});

router.post('/addUser', ctx => {
	const headers = ctx.request.body;
	console.log("body: " + headers.name);
	ctx.response.body = users[0];
	ctx.response.status = 200;
});

router.post('/updateDog', ctx => {
	const header = ctx.request.body;
	for (let i = 0; i < dogs.length; i++) {
		if (dogs[i].name === header.name) {
			dogs[i].race = header.race;
			dogs[i].personality = header.personality;
			dogs[i].age = header.age;
			ctx.response.body = dogs[i];
			console.log(JSON.stringify(dogs[i]))
			ctx.response.status = 200;
			break;
		}
	}
	console.log("dogs size " + dogs.length);
});


router.post('/removeDog', ctx => {

	const header = ctx.request.headers;
	console.log("Delete" + header.key);

	for (let i = 0; i < dogs.length; i++) {
		console.log(dogs[i].key);
		if (dogs[i].key === parseInt(header.key)) {
			ctx.response.body = dogs[i];
			console.log(JSON.stringify(dogs[i]))
			dogs.splice(i, 1);
			ctx.response.status = 200;
			break;
		}
	}

	console.log("dogs size " + dogs.length);
});

router.post('/addDog', ctx => {

	const header = ctx.request.body;
	let j = dogs.length
	dogs.push({
		key: j,
        name: header.name,
		race: header.race,
		personality: header.personality,
        age: header.age
	});
	ctx.response.body = dogs[dogs.length - 1];
	console.log("body: " + JSON.stringify(dogs[dogs.length - 1]));
	broadcast({message: "New Dog added!"});
	ctx.response.status = 200;
});

app.use(router.routes());
app.use(router.allowedMethods());

server.listen(3000);
