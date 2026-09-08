// ============================================================
// SEaled Classes and Interfaces
// ============================================================

// 1. What are we trying to achieve?
// ------------------------------------------------------------
// A sealed class/interface is used to restrict which classes
// are allowed to extend/implement it.
//
// Example:
// Only C, D and E are allowed to directly extend A.

sealed class A permits C, D, E {
}


// 2. Three modifiers for subclasses of a sealed class
// ------------------------------------------------------------
//
// final
// -> Cannot be extended further.
//
// sealed
// -> Can be extended, but only by the classes mentioned
//    in the permits clause.
//
// non-sealed
// -> Removes the restriction of the sealed parent.
//    Any class can extend it (normal access rules apply).


// 3. final example
// ------------------------------------------------------------

final class C extends A {
}

// C cannot be extended further.
// class X extends C { }   // ❌ Compilation Error


// 4. sealed example
// ------------------------------------------------------------

sealed class D extends A permits X, Y {
}

final class X extends D {
}

final class Y extends D {
}

// Only X and Y can directly extend D.


// 5. non-sealed example
// ------------------------------------------------------------

non-sealed class E extends A {
}

// Since E is non-sealed, any class can extend E.

class F extends E {
}

class G extends E {
}


// Hierarchy:
//
//              A (sealed)
//             /    |    \
//            C     D     E
//          final  sealed  non-sealed
//                  / \      / \
//                 X   Y    F   G
//
// C -> Cannot be extended
// D -> Only X and Y can extend
// E -> Any class can extend


// ============================================================
// SEALED INTERFACE
// ============================================================

// 6. Interfaces can also be sealed
// ------------------------------------------------------------

sealed interface H permits I, J {
}


// Direct subtypes of a sealed interface must be:
// final OR sealed OR non-sealed


// final
final class I implements H {
}

// I cannot be extended further.


// sealed
sealed interface J extends H permits K {
}

final class K implements J {
}


// non-sealed
// Example:
//
// non-sealed interface J extends H {
// }
//
// Any class/interface can extend/implement J.


// ============================================================
// IMPORTANT POINT
// ============================================================

// An interface itself cannot be final.
//
// final interface Test { }   // ❌ Compilation Error
//
// But a class implementing a sealed interface can be final:
//
// final class I implements H { }  // ✅


// ============================================================
// EASY WAY TO REMEMBER
// ============================================================
//
// sealed     -> Controlled
//              Only permitted classes can extend/implement.
//
// final      -> Closed
//              Cannot be extended further.
//
// non-sealed -> Open
//              Sealing restriction is removed.
//
//
// sealed  = controlled
// final   = closed
// non-sealed = open

class Demo{
    public static void main(String[] args) {
        
    }
}