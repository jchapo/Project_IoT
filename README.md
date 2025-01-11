# Proyecto: Sistema de Gestión de Inventario de Sitios y Equipos de Telecomunicaciones

Este proyecto consiste en el desarrollo de un sistema de gestión de inventario para sitios y equipos de telecomunicaciones. Está diseñado para gestionar la creación, edición, visualización y eliminación de sitios, así como la asignación de equipos a los mismos, dependiendo de si el sitio es de telecomunicación fija o móvil.

La solución fue implementada en **Android Nativo** utilizando **Java**. La base de datos utilizada para el almacenamiento de la información es **Firebase Firestore**, y para mejorar la experiencia de búsqueda se integró **Algolia**.

## 🎯 Funcionalidades

El sistema contempla la gestión de usuarios con diferentes roles, cada uno con capacidades y privilegios específicos:

### Roles de Usuario
- **Superadmin**: Gestión de usuarios administradores, visualización de logs de eventos.
- **Administrador**: Creación de usuarios, gestión de sitios, equipos y asignación de supervisores.
- **Supervisor**: Gestión de equipos en los sitios asignados, reportes de estado de equipos y seguimiento de incidentes.

### Funcionalidades Clave
- **Gestión de Sitios**: Crear, editar, visualizar y eliminar sitios con detalles como geolocalización, tipo de zona (urbana/rural), y tipo de sitio (móvil/fijo).
- **Gestión de Equipos**: Agregar, editar y eliminar equipos a cada sitio, con detalles como SKU, número de serie, marca, modelo, descripción y fotos del equipo.
- **Escaneo QR**: Los equipos pueden ser buscados mediante un código QR.
- **Sala de Chat**: Comunicación entre administradores y supervisores dentro de la plataforma.
- **Historial de Logs**: Registro de eventos del sistema, como llenado de formularios y otras acciones importantes.

## 🧰 Tecnologías Utilizadas
- **Android Nativo**: Desarrollo de la aplicación móvil utilizando Java.
- **Firebase**: Base de datos en la nube para almacenar información de usuarios, sitios y equipos.
- **Firestore**: Base de datos NoSQL utilizada para el almacenamiento y consulta de datos.
- **Algolia**: Servicio para búsquedas rápidas y eficientes en la base de datos.
  
## 📱 Requisitos
- **Sistema Operativo**: Android 10.0 o superior.
- **Compatibilidad**: Aplicación compatible con dispositivos Android.

## 📸 Capturas de Pantalla
A continuación, se presentan algunas capturas de pantalla que muestran la interfaz y funcionalidades del sistema.

![image](https://github.com/user-attachments/assets/146c2158-3822-4f62-a6f6-1bb1138d1694)
![image](https://github.com/user-attachments/assets/3b754a55-37a7-4eec-b547-e7c2fd7fbbbd)
![image](https://github.com/user-attachments/assets/4a0b8cba-3d85-4fc6-92a9-8fff9652ee5b)

