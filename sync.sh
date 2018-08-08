#!/usr/bin/env bash
git submodule update --init --recursive
git submodule foreach git checkout dev
git submodule foreach git pull
