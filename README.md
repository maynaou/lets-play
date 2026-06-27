# Let's Play API

API REST développée avec **Spring Boot** pour gérer l'authentification des utilisateurs et un catalogue de produits.

## Stack technique

- Java 17 / Spring Boot 3.3.2
- Spring Security (JWT, OAuth2 Resource Server)
- MongoDB (Spring Data MongoDB)
- HTTPS (keystore PKCS12)
- Docker / Docker Compose

## Installation et lancement

### 1. Configurer les variables d'environnement

Copier l'exemple et renseigner tes propres valeurs :

```bash
cp .env.example .env
```

Variables nécessaires dans `.env` :

```
MONGO_USERNAME=admin
MONGO_PASSWORD=ton_mot_de_passe
MONGO_DATABASE=mydatabase
JWT_SECRET=une_chaine_secrete_longue_et_aleatoire
JWT_EXPIRATION=900
SSL_KEYSTORE_PASSWORD=ton_mot_de_passe_keystore
```

### 2. Lancer avec Docker Compose

```bash
docker compose up --build
```

L'API sera accessible sur `https://localhost:8080`.

### 3. Lancer les tests

Nécessite une instance MongoDB locale (ex: `docker run -d -p 27017:27017 mongo:latest`).

```bash
cd backend
./mvnw test
```

## Endpoints principaux

### Authentification (`/api/auth`)

| Méthode | Endpoint              | Description                     |
|---------|------------------------|----------------------------------|
| POST    | `/api/auth/register`   | Créer un compte                 |
| POST    | `/api/auth/login`      | Se connecter (retourne un JWT)  |
| POST    | `/api/auth/refresh`    | Rafraîchir le token d'accès     |

### Utilisateurs (`/api/users`)

| Méthode | Endpoint           | Description                |
|---------|----------------------|-----------------------------|
| GET     | `/api/users/{id}`    | Récupérer un utilisateur   |
| PUT     | `/api/users/{id}`    | Modifier un utilisateur    |
| DELETE  | `/api/users/{id}`    | Supprimer un utilisateur   |

### Produits (`/api/products`)

| Méthode | Endpoint              | Description                                  |
|---------|------------------------|-----------------------------------------------|
| GET     | `/api/products`       | Lister tous les produits                     |
| GET     | `/api/products/{id}`  | Récupérer un produit                         |
| POST    | `/api/products`       | Créer un produit                             |
| PUT     | `/api/products/{id}`  | Modifier un produit (propriétaire ou admin)  |
| DELETE  | `/api/products/{id}`  | Supprimer un produit (propriétaire ou admin) |

## Authentification

Toutes les routes (sauf `/api/auth/**`) nécessitent un header :

```
Authorization: Bearer <token>
```

Le token est obtenu via `/api/auth/login`.
