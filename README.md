Ece651 Sp23 Team 8 Repo
======================================
[![pipeline status](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/badges/main/pipeline.svg)](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/pipelines)
[![coverage](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/badges/main/coverage.svg?job=test)](https://ys386.pages.oit.duke.edu/ece651-sp23-team8-riskgame/dashboard.html)

If you look inside the gradle-skeleton directory, you will see three directories

- client : the code for the client
- server : the code for the server
- shared : for code that is shared between the client and the server

You can run the client fro the top-level (gradle-skeleton directory)
with

./gradlew run-client

Likewise you can run the server with

./gradlew run-server

If you open the code for the provided files in Emacs and run test
coverage (C-c C-t) you will get test coverage for that particular
sub-project.  E.g., if you are editing server code, you will get
test coverage for the server code.  If you are editing shared
code, you will get test coverage for the shared code. That is
as-intended.


## Documents
[UML Diagram](https://www.canva.com/design/DAFb0hZmeyU/WHC6Jp9ISU1aHNBpZ1FC7Q/edit)

[Project Task List](https://docs.google.com/spreadsheets/d/1T11l-u_cKE7usFyzeh6nFgeQg2giplyMUG1rpR9TXeo/edit#gid=0)

[Prototype](https://www.icloud.com/keynote/061JqScAnVkA7e9KYzufytFaw#%E7%B0%A1%E5%A0%B1)

## Coverage
[Detailed coverage](https://ys386.pages.oit.duke.edu/ece651-sp23-team8-riskgame/dashboard.html)

## Guide
[Client&Server Guide](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/client_server.md)

[Quick Guide](https://gitlab.oit.duke.edu/ys386/ece651-sp23-team8-riskgame/-/blob/main/git_quick_reference.md)

[Gitlab CI Document](https://docs.gitlab.com/ee/ci/yaml/)

[Gitlab Runner](https://docs.gitlab.com/runner/)

[Dockerfile](https://docs.docker.com/engine/reference/builder/)

[Docker Run]( https://docs.docker.com/engine/reference/run/)
