DORM POC

Composed of two packages :
  - Core : represents the core with Dorm specific metadatas.
  - Maven : Dorm plugin represents the first use case of Dorm. Add maven specific metadatas and logic.

Core and plugins have to be independants and a plugin may be added or removed dynamically, without restarting the server or reload the core.
