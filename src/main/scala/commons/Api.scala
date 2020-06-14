package commons

trait Api{

  def login(username: String, password: String)

  def logout()

  def search()

  def download()
}
