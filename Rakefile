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
  classpath = ".:bin/:lib/google-http-client-1.6.0-beta.jar:lib/dependencies/jsr305-1.3.9.jar:lib/dependencies/gson-1.7.1.jar:lib/dependencies/guava-r09.jar:lib/dependencies/httpclient-4.0.3.jar:lib/dependencies/xpp3-1.1.4c.jar:lib/dependencies/protobuf-java-2.2.0.jar:lib/dependencies/junit-4.8.2.jar"
  # sh "java -cp #{classpath} org.junit.runner.JUnitCore io.spire.tests.SpireTest"
end

task :jar do
	sh "jar -cvf build/spire-io-client-#{$version}.jar bin/"
end

desc "generate docs and build a jar"
task :package => [:doc, :jar]

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

# Update the pages/ directory clone
file 'docs/.git' => ['docs/', '.git/refs/heads/gh-pages'] do |f|
    sh "cd docs && git init -q && git remote add origin git@github.com:spire-io/spire.io.rb.git" if !File.exist?(f.name)
    sh "cd docs && git fetch -q origin && git reset -q --hard origin/gh-pages && touch ."
end
