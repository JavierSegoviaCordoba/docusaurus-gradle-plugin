plugins { //
    id("com.javiersc.docusaurus") version "0.1.1-SNAPSHOT"
}

docusaurus {
    node {
        version.set("18.16.1")
        download.set(true)
    }
}

version = "1.3.4"
