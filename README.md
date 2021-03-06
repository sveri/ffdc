# FFDC

This is a template (not a leiningen) combining several projects to get started with.
Included are:
* friend with friendui
* datomic pro (should be easily replacable by datomic free edition)
* chestnut template with all the nice reloading stuff
* freactive instead of om
* secretary for cljs routing
* Bootstrap
* JQuery

## Datomic setup
Start a REPL (in a terminal: `lein repl`, or from Emacs: open a
clj/cljs file in the project, then do `M-x cider-jack-in`. Make sure
CIDER is up to date).

Open db.clj and copy the complete comment block into your repl and execute it. This should setup the datomic database.
Then restart the REPL.

## Running it

In the repl just enter:
(start-all)

## Logging in
Two users are provided:
* admin@localhost.de - admin role
* free@localhost.de - free role
Both with password: 'admin'

## Adapting it
The whole project is namespaced with de.sveri.structconverter. Find a replace should do its job to put it in your namespace
accordingly.

## FAQ
* I get this error: #<CompilerException java.lang.RuntimeException: Could not find db in catalog, compiling:(server.clj:46:12)
You did not initalize your database correctly. Look at friendui-config.edn for the uri and the partition id,
also into the two edn files in resources/db

## License
Distributed under the Eclipse Public License either version 1.0 or any later version.