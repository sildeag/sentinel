# Sentinel Architecture Enforcement

Sentinel is a lightweight, policy‑driven architecture enforcement tool for Kotlin
multi‑module projects. It scans your project structure, classifies modules,
analyzes imports, checks platform usage, validates UI/theme boundaries, enforces
DI rules, and verifies module‑to‑module dependencies.

Sentinel helps prevent architectural drift and keeps large codebases clean,
consistent, and maintainable.

---

## Features

### ✔ Policy‑Driven Architecture
Sentinel uses a single source of truth for module policy:

- `EnumModule` — defines all project modules  
- `ModulePolicyTable` — assigns each module a policy  
- Rule engines enforce architecture based on policy  

This makes Sentinel easy to extend and easy to maintain.

### ✔ Rule Engines
Sentinel includes six independent rule engines:

- **ImportRules** — UI, platform, STT, PDF, Skia, DI import restrictions  
- **PlatformRules** — Android/Desktop API boundaries  
- **ThemeRules** — typography, TextStyle, font weights, `sp` usage  
- **UIRules** — Compose UI, previews, UI functions  
- **DIRules** — DI frameworks, modules, constructs, repository impls  
- **DependencyRules** — module‑to‑module dependency validation  

### ✔ Module Classification
Sentinel maps Gradle module paths (e.g., `:ui-android`) to `EnumModule`
using `ModuleClassifier.kt`.

### ✔ Full Project Scan
Sentinel walks the project directory, scanning all `.kt` files:

- Extracts imports  
- Extracts UI/theme/platform/DI patterns  
- Extracts dependencies (future enhancement)  
- Runs all rule engines  
- Aggregates violations  

### ✔ Clear Reporting
Violations are printed with:

- Module name  
- Import violations  
- Dependency violations  
- Suggestions for correction  

## Running Sentinel

Sentinel can be executed in three different ways depending on how your project
is structured. All modes use the same entry point (`:architecture:run`) but the
context determines how Gradle resolves the task.

### 1. Running Sentinel from its own project directory
If Sentinel is a standalone project:

```powershell
cd sentinel
./gradlew :architecture:run --args="D:/Android/Projects/sound2text" > "sentinel-errors-$(Get-Date -Format 'yyyyMMdd-HHmmss').txt"
```
### 2. Running Sentinel as a module inside the target project (unavailable, AGP bug)

If Sentinel is included directly inside the sound2text repository:

```sound2text/
    architecture/
    core/
    ui-android/
    ...
```

Then you can run it from anywhere inside the repo:

```powershell
./gradlew :sentinel:architecture:run --args="D:/Android/Projects/sound2text"
```

### 3. Running Sentinel via composite build (recommended but unavailable, AGP bug)
If sound2text includes Sentinel using a composite build:

kotlin
```// sound2text/settings.gradle.kts
includeBuild("../sentinel")
```

Then Sentinel becomes available as an external build, and you can run it
directly from inside sound2text:
```
./gradlew :sentinel:architecture:run --args="D:/Android/Projects/sound2text"
```

## Architecture Overview

Sentinel’s architecture is policy‑driven and modular:

```mermaid
flowchart TD

    subgraph CLI["Main.kt / Gradle Task"]
        A["main(args)"]
        B["ArchitectureSentinel.run()"]
        A --> B
    end

    subgraph Sentinel["ArchitectureSentinel.kt"]
        B --> C["Scan project directory"]
        C --> D["Extract imports / patterns / dependencies"]
        D --> E["ArchitectureRules.analyzeFile()"]
    end

    subgraph Analysis["ArchitectureRules.kt"]
        E --> F1["ImportRules"]
        E --> F2["UIRules"]
        E --> F3["ThemeRules"]
        E --> F4["PlatformRules"]
        E --> F5["DIRules"]
        E --> F6["DependencyRules"]
    end

    subgraph Policies["ModulePolicyTable.kt"]
        P["ModulePolicyTable.policy[module]"]
    end

    subgraph Classification["ModuleClassifier.kt"]
        M["classifyEnumModule(path)"]
    end

    subgraph Patterns["Pattern Extractors"]
        PT1["UIPatterns"]
        PT2["ThemePatterns"]
        PT3["PlatformPatterns"]
        PT4["DIPatterns"]
        PT5["DependencyPatterns"]
    end

    %% Connections
    B --> M
    D --> PT1
    D --> PT2
    D --> PT3
    D --> PT4
    D --> PT5

    F1 --> P
    F2 --> P
    F3 --> P
    F4 --> P
    F5 --> P
    F6 --> P

    E --> R["FileAnalysisResult"]
    R --> O["Sentinel Report"]
