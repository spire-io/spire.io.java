require "rake/clean"

CLEAN << FileList["*.gem"]
CLEAN << FileList["docs/*"]
CLEAN << ".yardoc"

$version = File.read("VERSION").chomp

desc "run javadoc"
task :docs do
	sh "javadoc @argfile"
end

# Alias for doc task
task :doc => :docs

desc "run tests"
task :test do
  classpath = TaskHelpers.classpath
  sh "java -cp #{classpath} org.junit.runner.JUnitCore io.spire.tests.SpireTest"
end

desc "compile and run the tests using Ant"
task 'ant:test' do
  sh "ant test"
end

desc "creates the client jar file"
task :jar => ['build/'] do
  sh "jar -cvf build/spire-io-client-#{$version}.jar build/classes"
end

desc "creates a compressed tar file with all dependencies"
task :tarball do
  Dir.chdir 'build' do
    Dir.chdir 'dist' do
      sh "rm spire*.tar.gz"
      sh "tar -czvf spire-io-client.tar.gz ./*"
    end
  end
end

desc "compiles the source code using Ant"
task 'ant:build' do
  sh 'ant'
end

desc "generate docs, compiles source code and creates a jar"
task :package => [:doc, 'ant:build']

# Updates GITHUB PAGES
desc 'Update gh-pages branch'
task 'docs:pages' => ['docs/', 'docs/.git', :docs] do
  rev = `git rev-parse --short HEAD`.strip
  Dir.chdir 'docs' do
    last_commit = `git log -n1 --pretty=oneline`.strip
    message = "rebuild pages from #{rev}"
    result = last_commit =~ /#{message}/
    # generating yardocs causes updates/modifications in all the docs
    # even when there are changes in the docs (it updates the date/time)
    # So we check if the last commit message if the hash is the same do NOT update the docs
    if result
      verbose { puts "nothing to commit" }
    else
      sh "git add ."
      sh "git commit -m 'rebuild pages from #{rev}'" do |ok,res|
        if ok
          verbose { puts "gh-pages updated" }
          sh "git push -q origin HEAD:gh-pages"
        end
      end
    end
  end
end

file 'docs/' do |f|
  Dir.mkdir(f.name) if !File.exists?(f.name)
end

file 'build/' do |f|
  Dir.mkdir(f.name) if !File.exists?(f.name)
end

# Update the pages/ directory clone
file 'docs/.git' => ['docs/', '.git/refs/heads/gh-pages'] do |f|
    sh "cd docs && git init -q && git remote add origin git@github.com:spire-io/spire.io.java.git" if !File.exist?(f.name)
    sh "cd docs && git fetch -q origin && git reset -q --hard origin/gh-pages && touch ."
end

module TaskHelpers
  module_function
  
  def classpath
    cp = ".:build/classes"

    Dir.chdir 'lib' do |d0|
      Dir["*.jar"].each{ |file| cp += ":#{d0}/#{file}"  }

      Dir.chdir 'dependencies' do |d1|
        Dir["*.jar"].each{ |file| cp += ":#{d0}/#{d1}/#{file}"  }
      end
    end

    return cp
  end
end