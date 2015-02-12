module.exports = (grunt) ->

  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"

    watch:
      files: [
        "src/*/*.*"
        "src/*/*/*.*"
      ]
      tasks: [
        "compileFull"
      ]

    coffee:
      cdojAngularFull:
        options:
          join: true
        files:
          "temp/angular/cdoj.angular.js": [
            "src/angular/i18n/*.coffee"
            "src/angular/cdoj.coffee"
            "src/angular/config/*.coffee"
            "src/angular/controller/*/*.coffee"
            "src/angular/directive/*/*.coffee"
          ]
      cdojTest:
        options:
          join: true
        files:
          "temp/test/cdoj.test.js": [
            "src/test/*/*.coffee"
          ]

    coffeelint:
      coffee: [
        "src/angular/*.coffee"
        "src/angular/**/*.coffee"
      ]
      options:
        "arrow_spacing":
          "level": "error"
    less:
      cdoj:
        files:
          "temp/css/cdoj.css": "src/less/cdoj.less"

    concat:
      jasmineJS:
        src: [
          "dist/js/cdoj.js"
          "bower_components/jasmine/lib/jasmine-core/jasmine.js"
          "bower_components/jasmine/lib/jasmine-core/jasmine-html.js"
          "bower_components/jasmine/lib/jasmine-core/boot.js"
          "temp/test/cdoj.test.js"
        ]
        dest: "dist/js/cdoj.js"
      jasmineCSS:
        src: [
          "dist/css/cdoj.css"
          "bower_components/jasmine/lib/jasmine-core/jasmine.css"
        ]
        dest: "dist/css/cdoj.css"
      cdojCssFull:
        src: [
          "bower_components/angular-material/angular-material.css"
          "temp/css/cdoj.css"
        ]
        dest: "dist/css/cdoj.css"
      cdojJsNeedMinimized:
        src: [
          "temp/angular/cdoj.angular.js"
        ]
        dest: "temp/js/need-minimized.js"
      cdojJsFull:
        src: [
          "bower_components/angular/angular.js"
          "bower_components/angular-animate/angular-animate.js"
          "bower_components/angular-aria/angular-aria.js"
          "bower_components/angular-cookies/angular-cookies.js"
          "bower_components/angular-material/angular-material.js"
          "bower_components/angular-route/angular-route.js"
          "bower_components/underscore/underscore.js"
          "temp/angular/cdoj.angular.js"
        ]
        dest: "dist/js/cdoj.js"
      cdojJsMinimized:
        src: [
          "bower_components/angular/angular.min.js"
          "bower_components/angular-animate/angular-animate.min.js"
          "bower_components/angular-aria/angular-aria.min.js"
          "bower_components/angular-cookies/angular-cookies.min.js"
          "bower_components/angular-material/angular-material.min.js"
          "bower_components/angular-route/angular-route.min.js"
          "temp/js/underscore.min.js"
          "temp/js/need-minimized.min.js"
        ]
        dest: "dist/js/cdoj.min.js"

    cssmin:
      minimizeCss:
        src: "dist/css/cdoj.css"
        dest: "dist/css/cdoj.min.css"

    min:
      minimizeUnderscore:
        src: "bower_components/underscore/underscore.js"
        dest: "temp/js/underscore.min.js"
      minimizeScripts:
        src: "temp/js/need-minimized.js"
        dest: "temp/js/need-minimized.min.js"

  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.loadNpmTasks "grunt-contrib-less"
  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-concat"
  grunt.loadNpmTasks "grunt-yui-compressor"
  grunt.loadNpmTasks "grunt-coffeelint"

  grunt.registerTask "compileFull", [
    "coffeelint:coffee"

    "less:cdoj"
    "concat:cdojCssFull"

    "coffee:cdojAngularFull"
    "concat:cdojJsFull"
  ]
  grunt.registerTask "testEnvironment", [
    "compileFull"
    "coffee:cdojTest"

    "concat:jasmineJS"
    "concat:jasmineCSS"
  ]
  grunt.registerTask "minifyResult", [
    "cssmin:minimizeCss"

    "concat:cdojJsNeedMinimized"
    "min:minimizeUnderscore"
    "min:minimizeScripts"
    "concat:cdojJsMinimized"
  ]
  grunt.registerTask "default", [
    "build"
  ]
  grunt.registerTask "build", [
    "compileFull",
    "minifyResult"
  ]