db = db.getSiblingDB('plane_db');

db.createUser(
        {
            user: "plane-api",
            pwd: "plane-api-pass",
            roles: [
                {
                    role: "readWrite",
                    db: "plane_db"
                }
            ]
        }
);

db = db.getSiblingDB('flight_db');

db.createUser(
        {
            user: "flight-api",
            pwd: "flight-api-pass",
            roles: [
                {
                    role: "readWrite",
                    db: "flight_db"
                }
            ]
        }
);