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