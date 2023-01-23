import json, pymysql, os

class Parameters():
    def get_param_env(self, rule_id: str) -> list:
        rule = self.get_param_db(rule_id)
        obj = json.loads(rule)

        return obj.get('custom')

    def get_param_db(self, rule_id: str) -> str:
        conn = pymysql.connect(host='initcloud_db', port=int(os.environ['DB_PORT']), 
            user=os.environ['MARIADB_USER'], password=os.environ['MARIADB_PASSWORD'] 
            db='initcloud', charset='utf8')
            
        cur = conn.cursor()

        sql = f'SELECT custom_detail FROM custom_rule WHERE rule_id = %s'
        cur.execute(sql, rule_id)
        row = cur.fetchone()

        if row is None:
            return None

        return row[0]
