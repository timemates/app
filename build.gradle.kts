plugins {
    id(conventions.kover)
}

kover {
    merge {
        allProjects()
    }
}