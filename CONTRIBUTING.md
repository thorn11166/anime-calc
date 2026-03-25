# Contributing to AnimeCalc

We love contributions! Whether it's bug fixes, new features, or documentation, every contribution helps.

## Getting Started

1. **Fork** the repository
2. **Clone** your fork: `git clone https://github.com/YOUR_USERNAME/anime-calc.git`
3. **Create a branch**: `git checkout -b feature/your-feature`
4. **Install** dependencies and build

## Development Setup

```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/anime-calc.git
cd anime-calc

# Open in Android Studio
# File > Open > Select the anime-calc directory

# Build & test
./gradlew build
./gradlew installDebug
```

## Code Style

- **Kotlin Style Guide:** Follow official Kotlin conventions
- **Naming:** camelCase for functions/variables, PascalCase for classes
- **Comments:** Comment non-obvious logic, especially in calculation engine
- **Line Length:** Keep under 100 characters where possible

## Commit Messages

- Start with an emoji: `✨ (feature), 🐛 (bugfix), 📖 (docs), 🎨 (style)`
- Be descriptive: `✨ Add scientific mode` not `Update calculator`
- Reference issues: `Fixes #123` or `Related to #456`

## Pull Request Process

1. **Update** documentation if needed
2. **Test** your changes on multiple API levels
3. **Create** a PR with clear description
4. **Link** related issues
5. **Wait** for CI/CD to pass
6. **Address** review feedback

## Feature Requests

Open an issue with:
- Clear description of the feature
- Why it would be useful
- Example use cases
- Any implementation ideas

## Bug Reports

Please include:
- Device/API level
- Steps to reproduce
- Expected vs actual behavior
- Screenshots/videos if applicable
- Logcat output

## Testing

We encourage tests! Place them in `app/src/test/` or `app/src/androidTest/`

```kotlin
// Example test
@Test
fun testBasicAddition() {
    val engine = CalculatorEngine()
    engine.appendDigit('5')
    engine.setOperation('+')
    engine.appendDigit('3')
    assertEquals("8", engine.equals())
}
```

## Areas to Contribute

- 🎨 UI improvements and animations
- 🧮 New calculator modes (scientific, programmer, etc.)
- 🌍 Internationalization (i18n)
- 🧪 Unit tests
- 📚 Documentation
- 🐛 Bug fixes

## Questions?

Open an issue or start a discussion. We're happy to help!

---

**Thank you for contributing! 💗✨**
