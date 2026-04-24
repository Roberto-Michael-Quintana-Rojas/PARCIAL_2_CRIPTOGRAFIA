# 🔐 Proyecto Colaborativo — Cifrados Clásicos

Aplicación de escritorio en **JavaFX** que implementa múltiples cifrados clásicos de criptografía. Cada integrante del equipo desarrolla un cifrado de forma independiente en su propia rama de Git.


---

## 📁 Estructura del Proyecto

```
demo/src/main/
├── java/com/example/
│   ├── App.java                        ← ARCHIVO COMPARTIDO (no tocar sin coordinar)
│   ├── module-info.java                ← ARCHIVO COMPARTIDO (agregar solo tu opens)
│   ├── router/
│   │   └── Router.java                 ← ARCHIVO COMPARTIDO (agregar solo tu método)
│   ├── cipher/       --------------AQUI SE AGREGA LA LOGICA DE CIFRADO QUE TENDRA CADA UNO EN SU PROPIA CARPETA Y ARCHIVO
│   │   ├── Cipher.java                 ← INTERFAZ BASE (no modificar)
│   │   ├── Vigenere/
│   │   │   └── VigenereCipher.java     ← Solo toca: responsable Vigenère
│   │   ├── AES/
│   │   │   └── AESCipher.java          ← Solo toca: responsable AES
│   │   ├── Cesar/
│   │   │   └── CesarCipher.java        ← Solo toca: responsable César
│   │   ├── PigPen/
│   │   │   └── PigPenCipher.java       ← Solo toca: responsable PigPen
│   │   └── RailFence/
│   │       └── RailFenceCipher.java    ← Solo toca: responsable Rail Fence
│   └── UI/
│       ├── Vigenere/ --------------- AQUI SE MODIFICA EL CONTROLLER DE CADA CIFRADO Y SOLO ESO
│       │   └── VigenereController.java ← Solo toca: responsable Vigenère
│       ├── AES/
│       │   └── AESController.java      ← Solo toca: responsable AES
│       ├── Cesar/
│       │   └── CesarController.java    ← Solo toca: responsable César
│       ├── PigPen/
│       │   └── PigPenController.java   ← Solo toca: responsable PigPen
│       └── RailFence/
│           └── RailFenceController.java← Solo toca: responsable Rail Fence
└── resources/com/example/
    └── UI/
        ├── Vigenere/ ----------AQUI SE AGREGA EL CODIGO DE LA INTERFAZ QUE VAS A GENERAR PARA TU CIFRADO
        │   └── Vigenere.fxml           ← Solo toca: responsable Vigenère
        ├── AES/
        │   └── AES.fxml                ← Solo toca: responsable AES
        ├── Cesar/
        │   └── Cesar.fxml              ← Solo toca: responsable César
        ├── PigPen/
        │   └── PigPen.fxml             ← Solo toca: responsable PigPen
        └── RailFence/
            └── RailFence.fxml          ← Solo toca: responsable Rail Fence
```

---

## 👥 Responsabilidades por Integrante

| Cifrado    | Archivos exclusivos de esa persona                                                                 |
|------------|----------------------------------------------------------------------------------------------------|
| Vigenère   | `cipher/Vigenere/`, `UI/Vigenere/`, `resources/.../Vigenere/`                                      |
| AES        | `cipher/AES/`, `UI/AES/`, `resources/.../AES/`                                                     |
| César      | `cipher/Cesar/`, `UI/Cesar/`, `resources/.../Cesar/`                                               |
| PigPen     | `cipher/PigPen/`, `UI/PigPen/`, `resources/.../PigPen/`                                            |
| Rail Fence | `cipher/RailFence/`, `UI/RailFence/`, `resources/.../RailFence/`                                   |

---

## ⚠️ Archivos Compartidos — Reglas Estrictas

Estos archivos son usados por **todos** y requieren coordinación antes de modificarlos.

### 🚫 `cipher/Cipher.java` — NO MODIFICAR NUNCA
Es la interfaz base que todos implementan. Cambiarla rompe el código de todos.

```java
public interface Cipher {
    String encrypt(String text);
    String decrypt(String text);
}
```

---

### ⚡ `module-info.java` — Solo agregar tu línea `opens`
Cada integrante **únicamente agrega** la línea correspondiente a su paquete. No eliminar ni modificar las líneas de los demás.

```java
module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example;

    // Cada integrante agrega SOLO su línea:
    opens com.example.UI.Vigenere to javafx.fxml;   // ← Vigenère
    opens com.example.UI.AES to javafx.fxml;         // ← AES
    opens com.example.UI.Cesar to javafx.fxml;       // ← César
    opens com.example.UI.PigPen to javafx.fxml;      // ← PigPen
    opens com.example.UI.RailFence to javafx.fxml;   // ← Rail Fence
}
```

---

### ⚡ `Router.java` — Solo agregar tu método `goToXxx()`
Cada integrante **únicamente agrega** su propio método. No tocar los métodos existentes.

```java
// Ejemplo del patrón a seguir:
public static void goToVigenere() {
    try {
        var url = Router.class.getResource("/com/example/UI/Vigenere/Vigenere.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Scene scene = new Scene(loader.load(), 450, 350);
        stage.setScene(scene);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
```

---

### ⚡ `App.java` — Solo el responsable del main lo modifica
Actualmente navega directo a Vigenère. Cuando se integre una pantalla principal con menú, este archivo será actualizado por el líder del proyecto. **No cambiar la lógica de inicio sin coordinarlo.**

---

## 🌿 Flujo de Trabajo con Git

```bash
# 1. Clonar el repositorio
git clone <url-del-repo>
cd SegundoParcial/demo

# 2. Crear tu rama con el nombre de tu cifrado
git checkout -b feature/cesar          # ejemplo para César
git checkout -b feature/aes            # ejemplo para AES
git checkout -b feature/pigpen
git checkout -b feature/railfence

# 3. Trabajar SOLO en tus archivos
# (ver tabla de responsabilidades arriba)

# 4. Hacer commit y push de tu rama
git add .
git commit -m "feat: implementar cifrado César"
git push origin feature/cesar

# 5. Crear un Pull Request hacia main cuando esté listo
```

> **Nunca hagas push directo a `main`.** Todo se integra mediante Pull Request.

---

## 🔧 Cómo Agregar tu Cifrado (paso a paso)

Sigue estos 4 pasos al implementar tu cifrado:

**Paso 1** — Crea tu clase de cifrado implementando la interfaz `Cipher`:
```
src/main/java/com/example/cipher/TuCifrado/TuCifradoCipher.java
```

**Paso 2** — Crea tu controlador JavaFX:
```
src/main/java/com/example/UI/TuCifrado/TuCifradoController.java
```

**Paso 3** — Crea tu vista FXML (mínimo: campo de texto, campo de clave si aplica, botón cifrar, campo resultado):
```
src/main/resources/com/example/UI/TuCifrado/TuCifrado.fxml
```

**Paso 4** — Registra tu cifrado en los archivos compartidos (una sola línea en cada uno):
- En `module-info.java`: `opens com.example.UI.TuCifrado to javafx.fxml;`
- En `Router.java`: agrega el método `goToTuCifrado()`

---

## 🧪 Guía de Testing

### Requisitos previos
- Java 17+
- Maven instalado
- JavaFX configurado en el proyecto (ya incluido en `pom.xml`)

---

### Ejecutar la aplicación

```bash
cd demo
mvn clean javafx:run

o buscar la el archivo app.java y ahi deberia aparecer "run" para su compilacion
```

---

### Probar tu cifrado manualmente

Una vez abierta la ventana de tu cifrado, prueba los siguientes casos:

#### ✅ Casos básicos

| Caso | Entrada | Clave | Resultado esperado |
|------|---------|-------|--------------------|
| Solo letras mayúsculas | `HELLO` | `KEY` | Resultado cifrado sin errores |
| Solo letras minúsculas | `hello` | `key` | Debe convertirse y cifrarse |
| Texto con espacios | `HELLO WORLD` | `KEY` | Espacios deben conservarse tal cual |
| Texto con números | `HELLO123` | `KEY` | Números y símbolos pasan sin cifrar |
| Clave de 1 carácter | `HELLO` | `A` | Debe funcionar (equivale a César) |

#### ✅ Caso de verificación cifrado/descifrado

El texto descifrado debe ser **idéntico al texto original**:

```
Texto original  →  cifrar(texto, clave)  →  texto cifrado
Texto cifrado   →  descifrar(resultado, clave)  →  texto original ✓
```

#### ❌ Casos borde a verificar

- Campo de texto **vacío** → no debe lanzar excepción, debe mostrar cadena vacía
- Clave **vacía** → manejar con mensaje de error o no hacer nada
- Clave más larga que el texto → debe funcionar correctamente
- Texto con caracteres especiales (`!`, `@`, `ñ`, `á`) → deben conservarse sin cifrar

---

### Verificar en consola

Al correr la app, el log de consola debe mostrar:

```
RUTA: file:/.../.../Vigenere.fxml   ← Confirma que el FXML se encontró
```

Si aparece `RUTA: null`, el archivo FXML no está en la ruta correcta dentro de `resources/`.

Si aparece `LoadException` con `IllegalAccessException`, falta agregar el `opens` en `module-info.java`.

---

### Prueba de integración (antes de hacer PR a main)

Antes de hacer merge a `main`, confirma que:

- [ ] La aplicación compila sin errores: `mvn clean compile`
- [ ] La ventana de tu cifrado abre sin pantalla en blanco
- [ ] Cifrar y descifrar un texto produce el resultado correcto
- [ ] Los campos vacíos no crashean la aplicación
- [ ] Tu código no modifica ningún archivo fuera de tu carpeta asignada
- [ ] Agregaste **una sola línea** en `module-info.java` y `Router.java`

---

## 🛠️ Tecnologías

- **Java 17**
- **JavaFX 17+**
- **Maven**
- **FXML** para las vistas